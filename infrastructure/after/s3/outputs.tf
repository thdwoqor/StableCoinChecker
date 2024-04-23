output s3_iam_arn {
  value = aws_iam_policy.s3.arn
}

output s3_bucket {
  value = aws_s3_bucket.this.bucket
}
