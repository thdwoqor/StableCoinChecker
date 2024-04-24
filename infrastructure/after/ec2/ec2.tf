resource "aws_instance" "prod" {
  ami                         = "ami-02c956980e9e063e5"
  instance_type               = "t2.small"
  associate_public_ip_address = true
  key_name                    = var.key_name
  subnet_id                   = data.terraform_remote_state.vpc.outputs.public_subnet_id[0]
  vpc_security_group_ids      = [aws_security_group.prod.id]
  user_data                   = file("./launch-instance.sh")
  iam_instance_profile        = aws_iam_instance_profile.this.name
  tags                        = var.tags
}

resource "aws_security_group" "prod" {
  name   = var.ec2_prod_name
  vpc_id = data.terraform_remote_state.vpc.outputs.vpc_id
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

resource "aws_iam_role_policy_attachment" "ec2" {
  policy_arn = data.terraform_remote_state.s3.outputs.s3_iam_arn
  role       = aws_iam_role.ec2.name
}
