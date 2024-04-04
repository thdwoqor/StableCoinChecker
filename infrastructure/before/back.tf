terraform {
  backend "s3" {
    bucket = "stable-coin-checker"
    key = "terraform/terraform.tfstate"
    region = "ap-northeast-2"
  }
}
