#ifndef QUEUE_H
#define QUEUE_H

typedef struct Node {
  unsigned int key;
  struct Node* next;
} Node;

typedef struct {
  struct Node* front;
  struct Node* rear;
} Queue;

Node* newNode(unsigned int key);

Queue* newQueue();

#endif