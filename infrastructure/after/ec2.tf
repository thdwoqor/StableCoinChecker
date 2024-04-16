resource "aws_instance" "prod" {
  ami                         = "ami-02c956980e9e063e5"
  instance_type               = "t2.micro"
  associate_public_ip_address = true
  key_name                    = var.project_name
  subnet_id                   = module.vpc.public_subnets[0]
  vpc_security_group_ids      = [aws_security_group.prod.id]
  user_data                   = file("./launch-instance.sh")
  iam_instance_profile        = aws_iam_instance_profile.prod.name
  tags                        = var.tags
}

resource "aws_security_group" "prod" {
  name = var.ec2_prod_name
  vpc_id = module.vpc.vpc_id
}

resource "aws_security_group_rule" "prod_ssh" {
  type              = "ingress"
  from_port         = 22
  to_port           = 22
  protocol          = "TCP"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.prod.id

  lifecycle { create_before_destroy = true }
}

resource "aws_security_group_rule" "prod_blue" {
  type              = "ingress"
  from_port         = 8080
  to_port           = 8080
  protocol          = "TCP"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.prod.id

  lifecycle { create_before_destroy = true }
}

resource "aws_security_group_rule" "prod_green" {
  type              = "ingress"
  from_port         = 8081
  to_port           = 8081
  protocol          = "TCP"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.prod.id

  lifecycle { create_before_destroy = true }
}

resource "aws_security_group_rule" "prod_all" {
  type              = "egress"
  from_port         = 0
  to_port           = 0
  protocol          = "-1"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.prod.id

  lifecycle { create_before_destroy = true }
}

resource "aws_iam_instance_profile" "prod" {
  name = var.ec2_prod_name
  role = aws_iam_role.prod.name
}

resource "aws_iam_role" "prod" {
  name = var.ec2_prod_name

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

resource "aws_iam_role_policy_attachment" "prod" {
  policy_arn = aws_iam_policy.s3.arn
  role       = aws_iam_role.prod.name
}
