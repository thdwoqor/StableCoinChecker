resource "aws_codebuild_project" "this" {
  name          = var.project_name
  build_timeout = 5
  service_role  = aws_iam_role.codebuild.arn

  artifacts {
    type = "NO_ARTIFACTS"
  }

  environment {
    compute_type    = "BUILD_GENERAL1_SMALL"
    image           = "aws/codebuild/amazonlinux2-x86_64-standard:4.0"
    type            = "LINUX_CONTAINER"
    image_pull_credentials_type = "CODEBUILD"
  }
  source {
    type            = "GITHUB"
    location        = var.github_url
    git_clone_depth = 1

    buildspec = "buildspec.yml"

    git_submodules_config {
      fetch_submodules = true
    }
  }

  tags = var.tags

  source_version = var.branch_name
}

resource "aws_iam_role" "codebuild" {
  name               = "codebuild-role"
  assume_role_policy = jsonencode(
    {
      "Version" : "2012-10-17",
      "Statement" : [
        {
          "Effect" : "Allow",
          "Principal" : {
            "Service" : "codebuild.amazonaws.com"
          },
          "Action" : "sts:AssumeRole"
        }
      ]
    })
}

resource "aws_iam_role_policy" "codebuild" {
  name = "codebuild-role-policy"
  role = aws_iam_role.codebuild.id

  policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Effect": "Allow",
        "Action": "codestar-connections:UseConnection",
        "Resource": "*"
      },
      {
        "Effect" : "Allow",
        "Resource" : [
          "*"
        ],
        "Action" : [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:PutLogEvents"
        ]
      },
      {
        "Effect" : "Allow",
        "Resource" : [
          "*"
        ],
        "Action" : [
          "s3:PutObject",
          "s3:GetObject",
          "s3:GetObjectVersion",
          "s3:GetBucketAcl",
          "s3:GetBucketLocation",
        ]
      },
    ]
  })
}
