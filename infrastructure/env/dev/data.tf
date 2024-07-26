data "terraform_remote_state" "vpc" {
  backend = "local"
  config  = {
    path = "../vpc/terraform.tfstate"
  }
}

data "terraform_remote_state" "s3" {
  backend = "local"
  config  = {
    path = "../s3/terraform.tfstate"
  }
}
