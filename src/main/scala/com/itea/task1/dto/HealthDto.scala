package com.itea.task1.dto

case class HealthDto(label: String, status: Boolean) {
  override def toString: String = s"""{ label: '$label', status: $status }"""
}
