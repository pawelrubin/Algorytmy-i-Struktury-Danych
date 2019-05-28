#include "queue.h"
#include <stdlib.h>

Node* newNode(unsigned int key) {
    Node* node = malloc(sizeof(Node*));
    node->key = key;
    node->next = NULL;
    return node;
}

Queue* newQueue() {
    Queue* queue = malloc(sizeof(Queue*));
    queue->front = queue->rear = NULL;
    return queue;
}

void enqueue(Queue* queue, unsigned int key) {
    Node* node = newNode(key);

    if (queue->rear == NULL) {
        queue->front = queue->rear = node;
    } else {
        queue->rear->next = node;
        queue->rear = node;
    }   
}

Node* dequeue(Queue* queue) {
    if (queue->front == NULL) {
        return NULL;
    }

    Node* node = queue->front;
    queue->front = queue->front->next;

    if (queue->front == NULL) {
        queue->rear = NULL;
    }

    return node;
}

