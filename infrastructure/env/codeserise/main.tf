module "code_series" {
  source            = "../../modules/codeserise"
  branch_name       = var.branch_name
  github_url        = var.github_url
  repo_path         = var.repo_path
  instance_tag_name = var.instance_tag_name
  project_name      = var.project_name
  tags              = var.tags
  s3_bucket_id      = data.terraform_remote_state.s3.outputs.s3_id
  s3_iam_arn        = data.terraform_remote_state.s3.outputs.s3_iam_arn
}
