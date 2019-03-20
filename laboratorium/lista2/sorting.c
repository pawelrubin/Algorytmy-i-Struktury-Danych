/**
 * @file sorting.c
 * @author Paweł Rubin
 * @date 2019-03-20
 * 
 * @copyright Copyright (c) 2019
 * 
 */
#include "sorting.h"

Settings* init_settings() {
  Settings* settings = malloc(sizeof(Settings));
  settings->asc_flag = -1;
  settings->type = -1;
  settings->k = -1;
  settings->file_name = NULL;
  return settings;
}

Data* init_data(size_t n) {
  Data* data = malloc(sizeof(Data));
  data->n = n;
  data->array = malloc(n * sizeof(int));
  return data;
}
