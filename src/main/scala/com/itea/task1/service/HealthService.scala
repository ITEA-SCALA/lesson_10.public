package com.itea.task1.service

import com.itea.task1.dao.HealthDao
import com.itea.task1.dto.HealthDto
import io.bfil.automapper.automap

class HealthService(implicit dao: HealthDao) {
  def get: HealthDto = automap(dao.get).to[HealthDto]
}
