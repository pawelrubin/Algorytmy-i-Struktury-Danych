#!/usr/bin/env sh

time glpsol -m matchglpk8_8.mod > glpkmatch8_8.txt
time glpsol -m matchglpk9_8.mod > glpkmatch9_8.txt
time glpsol -m matchglpk10_8.mod > glpkmatch10_8.txt