resource "aws_key_pair" "this" {
  key_name = var.project_name
  public_key = file(var.public_key_path)
}

output "key_pair_id"{
  value = aws_key_pair.this.id
}
