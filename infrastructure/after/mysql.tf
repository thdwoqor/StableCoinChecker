resource "aws_instance" "mysql" {
  ami                         = "ami-02c956980e9e063e5"
  instance_type               = "t2.micro"
  associate_public_ip_address = true
  key_name                    = var.project_name
  subnet_id                   = module.vpc.private_subnets[0]
  vpc_security_group_ids      = [aws_security_group.mysql.id]
  user_data                   = file("./launch-mysql-instance.sh")
  iam_instance_profile        = aws_iam_instance_profile.mysql.name
  tags                        = var.tags
}

resource "aws_security_group" "mysql" {
  name   = var.ec2_mysql_name
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

resource "aws_iam_instance_profile" "mysql" {
  name = var.ec2_mysql_name
  role = aws_iam_role.mysql.name
}

resource "aws_iam_role" "mysql" {
  name = var.ec2_mysql_name

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

resource "aws_iam_role_policy_attachment" "mysql" {
  policy_arn = aws_iam_policy.s3.arn
  role       = aws_iam_role.mysql.name
}
