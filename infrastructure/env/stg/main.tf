##################
### VPC
##################
module "vpc" {
  source = "terraform-aws-modules/vpc/aws"

  name = var.vpc_name
  cidr = "10.0.0.0/16"

  azs             = ["ap-northeast-2a", "ap-northeast-2c"]
  public_subnets  = ["10.0.1.0/24", "10.0.2.0/24"]
  private_subnets = ["10.0.3.0/24", "10.0.4.0/24"]

  enable_nat_gateway     = false
  single_nat_gateway     = false
  one_nat_gateway_per_az = false

  map_public_ip_on_launch = true
  enable_dns_hostnames    = true
  enable_dns_support      = true
  create_igw              = true

  tags = var.tags
}

##################
### S3
##################
module "s3_bucket" {
  source = "terraform-aws-modules/s3-bucket/aws"

  bucket        = var.bucket_name
  force_destroy = true
}

###################
#### Database
###################
resource "aws_instance" "mysql" {
  ami                         = "ami-02c956980e9e063e5"
  instance_type               = "t2.micro"
  associate_public_ip_address = true
  key_name                    = var.mysql_key_name
  subnet_id                   = module.vpc.private_subnets[0]
  vpc_security_group_ids      = [aws_security_group.mysql.id]
  user_data                   = file("./mysql_user_data.sh")
  iam_instance_profile        = aws_iam_instance_profile.mysql.name
  tags                        = {
    Name = var.mysql_ec2_name
  }
}

###################
#### Application
###################
resource "aws_instance" "dev" {
  ami                         = "ami-02c956980e9e063e5"
  instance_type               = "t2.small"
  associate_public_ip_address = true
  key_name                    = var.dev_key_name
  subnet_id                   = module.vpc.public_subnets[0]
  vpc_security_group_ids      = [aws_security_group.dev.id]
  user_data                   = file("./deployment_user_data.sh")
  iam_instance_profile        = aws_iam_instance_profile.this.name
  tags                        = {
    Name = var.deployment
  }
}

##################
### CI/CD
##################
module "code_series" {
  source            = "../../modules/codeserise"
  branch_name       = var.branch_name
  github_url        = var.github_url
  repo_path         = var.repo_path
  instance_tag_name = var.deployment
  project_name      = var.code_series_name
  tags              = var.tags
  s3_bucket_id      = module.s3_bucket.s3_bucket_id
}

##################
### Key Pair
##################
resource "aws_key_pair" "this" {
  key_name   = var.dev_key_name
  public_key = file(var.public_key_path)
}

##########################
#### Security Group Mysql
##########################
resource "aws_security_group" "mysql" {
  name   = var.mysql_ec2_name
  vpc_id = module.vpc.vpc_id
}

resource "aws_security_group_rule" "mysql_default" {
  type              = "ingress"
  from_port         = 3306
  to_port           = 3306
  protocol          = "TCP"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.mysql.id

  lifecycle { create_before_destroy = true }
}

resource "aws_security_group_rule" "mysql_ssh" {
  type              = "ingress"
  from_port         = 22
  to_port           = 22
  protocol          = "TCP"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.mysql.id

  lifecycle { create_before_destroy = true }
}

resource "aws_security_group_rule" "mysql" {
  type              = "egress"
  from_port         = 0
  to_port           = 0
  protocol          = "-1"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.mysql.id

  lifecycle { create_before_destroy = true }
}

##########################
#### Security Group Dev
##########################
resource "aws_security_group" "dev" {
  name   = var.ec2_prod_name
  vpc_id = module.vpc.vpc_id
}

resource "aws_security_group_rule" "prod_ssh" {
  type              = "ingress"
  from_port         = 22
  to_port           = 22
  protocol          = "TCP"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.dev.id

  lifecycle { create_before_destroy = true }
}

resource "aws_security_group_rule" "http" {
  type              = "ingress"
  from_port         = 80
  to_port           = 80
  protocol          = "TCP"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.dev.id

  lifecycle { create_before_destroy = true }
}

resource "aws_security_group_rule" "https" {
  type              = "ingress"
  from_port         = 443
  to_port           = 443
  protocol          = "TCP"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.dev.id

  lifecycle { create_before_destroy = true }
}

resource "aws_security_group_rule" "prod_all" {
  type              = "egress"
  from_port         = 0
  to_port           = 0
  protocol          = "-1"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.dev.id

  lifecycle { create_before_destroy = true }
}

##################
#### IAM Prod
##################
resource "aws_iam_instance_profile" "this" {
  name = var.ec2_prod_name
  role = aws_iam_role.ec2.name
}

resource "aws_iam_role" "ec2" {
  name = "ec2-role-2"

  assume_role_policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        Action    = "sts:AssumeRole"
        Effect    = "Allow"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_policy" "s3" {
  name = var.project_name

  policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        "Action" : [
          "s3:PutObject",
          "s3:GetObject",
          "s3:ListBucket",
          "s3:DeleteObject",
          "s3:PutObjectAcl"
        ],
        Effect   = "Allow",
        Resource = ["*"]
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "ec2" {
  policy_arn = aws_iam_policy.s3.arn
  role       = aws_iam_role.ec2.name
}

##################
#### IAM Mysql
##################
resource "aws_iam_instance_profile" "mysql" {
  name = var.mysql_ec2_name
  role = aws_iam_role.mysql.name
}

resource "aws_iam_role" "mysql" {
  name = var.mysql_ec2_name

  assume_role_policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        Action    = "sts:AssumeRole"
        Effect    = "Allow"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      }
    ]
  })
}
