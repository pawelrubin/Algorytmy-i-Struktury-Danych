#ifndef HYPERCUBE_H
#define HYPERCUBE_H

static const unsigned char BitsSetTable256[256] = 
{
#   define B2(n) n,     n+1,     n+1,     n+2
#   define B4(n) B2(n), B2(n+1), B2(n+1), B2(n+2)
#   define B6(n) B4(n), B4(n+1), B4(n+1), B4(n+2)
    B6(0), B6(1), B6(1), B6(2)
};

static inline unsigned char hamming(unsigned int v) {
	unsigned char * p = (unsigned char *) &v;
	unsigned char c = BitsSetTable256[p[0]] + 
										BitsSetTable256[p[1]] + 
										BitsSetTable256[p[2]] +	
										BitsSetTable256[p[3]];
	return c;
}

int** generate_cube(int k);

void print_cube(int** cube, int SIZE);

void test_gerating(int k, int t);

#endif