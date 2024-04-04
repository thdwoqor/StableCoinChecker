variable "security_name" {
    type = string
}

variable "ingress"{
    type = map
}

variable "protocol" {
    type = string
}

variable "cidr_blocks"{
    type = list
}

variable "key_name"{
    type = string
}


variable "path_to_public_key"{
    type = string
}

variable "region"{
    type = string
}

variable "tags"{
    type = map
}

variable "github" {
    type = string
}

variable "telegram_chatId" {
    type = string
}

variable "telegram_token" {
    type = string
}

variable "repository" {
    type = string
}

variable "server_port" {
    type = string
}

variable "project_name" {
    type = string
}
