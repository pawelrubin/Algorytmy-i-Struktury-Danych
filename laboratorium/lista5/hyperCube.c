#include "hyperCube.h"
#include <time.h>
#include <stdlib.h>
#include <stdio.h>

int** generate_cube(int k) {
	int** t = malloc((1 << k) * sizeof(int*));
	for (int i = 0; i < (1 << k); i++) {
		t[i] = malloc(k * sizeof(int));
		unsigned int h = hamming(i);
		unsigned int rand_range = (h + 1 > k - h ? (1 << (h + 1)) : (1 << (k - h)));
		for (int j = 0; j < k; j++) {
			t[i][j] = (!((1 << j) & i)) ? rand() % rand_range + 1 : 0;
		}
	}
	return t;
}

void print_cube(int** cube, int SIZE) {
	for (int i = 0; i < (1 << SIZE); i++) {
		for (int j = 0; j < SIZE; j++) {
			printf("%d ", cube[i][j]);
		}
		printf("\n");
	}
}

void test_gerating(int k, int t) {
	double time = 0;
	for (int i = 0; i < t; i++) {
		clock_t start = clock();
		generate_cube(k);
		clock_t end = clock();
		time += (double)(end - start) / CLOCKS_PER_SEC;
	}
	printf("%f", time/100);
}


