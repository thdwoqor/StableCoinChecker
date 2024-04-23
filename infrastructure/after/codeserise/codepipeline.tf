resource "aws_codestarconnections_connection" "this" {
  name          = var.project_name
  provider_type = "GitHub"
}

resource "aws_codepipeline" "this" {
  name     = var.project_name
  role_arn = aws_iam_role.codepipeline.arn

  artifact_store {
    location = data.terraform_remote_state.s3.outputs.s3_bucket
    type     = "S3"
  }

  stage {
    name = "Source"

    action {
      name             = "Source"
      category         = "Source"
      owner            = "AWS"
      provider         = "CodeStarSourceConnection"
      version          = "1"
      output_artifacts = ["source-artifact"]

      configuration = {
        ConnectionArn    = aws_codestarconnections_connection.this.arn
        FullRepositoryId = var.full_repository_id
        BranchName       = var.branch_name

        OutputArtifactFormat = "CODEBUILD_CLONE_REF"
      }
    }
  }

  stage {
    name = "Build"
    action {
      name             = "WebServerBuild"
      category         = "Build"
      owner            = "AWS"
      provider         = "CodeBuild"
      version          = "1"
      input_artifacts  = ["source-artifact"]
      output_artifacts = ["web-server-build-artifacts"]
      configuration    = {
        ProjectName = aws_codebuild_project.this.name
      }
    }
  }

  stage {
    name = "Deploy"

    action {
      name             = "WebserverDeploy"
      category         = "Deploy"
      owner            = "AWS"
      provider         = "CodeDeploy"
      version          = "1"
      input_artifacts  = ["web-server-build-artifacts"]
      output_artifacts = []
      configuration    = {
        ApplicationName     = aws_codedeploy_app.this.name
        DeploymentGroupName = aws_codedeploy_deployment_group.this.deployment_group_name
      }
    }
  }

  tags = var.tags
}


resource "aws_iam_role" "codepipeline" {
  name = "codepipeline-role"

  assume_role_policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Effect" : "Allow",
        "Principal" : {
          "Service" : "codepipeline.amazonaws.com"
        },
        "Action" : "sts:AssumeRole"
      }
    ]
  })
  tags = {
    project = "web server"
  }
}

resource "aws_iam_role_policy" "codepipeline" {
  name = "codepipeline-role-policy"
  role = aws_iam_role.codepipeline.id

  policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Effect" : "Allow",
        "Action" : [
          "codebuild:BatchGetBuilds",
          "codebuild:StartBuild",
        ],
        "Resource" : aws_codebuild_project.this.arn
      },
      {
        "Effect" : "Allow",
        "Action" : [
          "codestar-connections:UseConnection"
        ]
        "Resource" : [
          "${aws_codestarconnections_connection.this.arn}"
        ]
      },
      {
        "Effect" : "Allow",
        "Action" : [
          "codedeploy:*"
        ]
        "Resource" : [
          "*"
        ]
      },
    ]
  })
}

resource "aws_iam_role_policy_attachment" "codepipeline" {
  role       = aws_iam_role.codepipeline.id
  policy_arn = data.terraform_remote_state.s3.outputs.s3_iam_arn
}
