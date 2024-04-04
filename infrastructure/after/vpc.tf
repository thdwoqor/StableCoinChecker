module "vpc" {
  source = "terraform-aws-modules/vpc/aws"

  name = "test"
  cidr = "10.0.0.0/16"

  azs            = ["ap-northeast-2a", "ap-northeast-2c"]
  public_subnets = ["10.0.10.0/24", "10.0.20.0/24"]

  map_public_ip_on_launch = true
  enable_nat_gateway      = false
  enable_dns_hostnames    = true
  enable_dns_support      = true
  create_igw              = true

  tags = var.tags
}
