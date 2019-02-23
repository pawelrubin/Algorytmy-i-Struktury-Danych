/**
 * @file linked_list.h
 * @author Paweł Rubin
 * @date 2019-02-23
 * 
 * @copyright Copyright (c) 2019
 * 
 */

#ifndef LINKEDLIST_H
#define LINKEDLIST_H

typedef struct Node Node;
typedef struct Linked_list Linked_list;

/**
 * @brief Structure representing a node of the linked list.
 * 
 */
struct Node {
  int value;    /**< Value stored in the node. */
  Node *next;   /**< A pointer to the next node in the list. */
  Node *prev;   /**< A pointer to the previous node in the list. */
};

/**
 * @brief Structure representing a linked list of any kind.
 * 
 */
struct Linked_list {
  Node *head;   /**< A pointer to the head node of the list.*/
  
  void (*insert)(Linked_list *, int value);     /**< A pointer to insert method*/
  void (*delete)(Linked_list *, int value);     /**< A pointer to delete method*/
  int (*findMTF)(Linked_list *, int value);     /**< A pointer to findMTF method*/
  int (*findTRANS)(Linked_list *, int value);   /**< A pointer to findTRANS method*/
  int (*is_empty)(Linked_list *);               /**< A pointer to is_empty method*/
};

/**
 * @brief Node constructor.
 * 
 * Initialize structure members with NULL values.
 * 
 * @return A new Node
 */
Node new_node();

/**
 * @brief Linked_list constructor for singly linked list.
 * 
 * Initialize functions to singly linked list implementation.
 * 
 * @return A fresh new singly Linked_list 
 */
Linked_list new_singly_linked_list();

/**
 * @brief Insert operation implementation for singly Linked_list.
 * 
 * @param list A pointer to this list.
 * @param value A value to be inserted.
 */
void insert_singly(Linked_list *list, int value);

/**
 * @brief Delete operation implementation for singly Linked_list.
 * 
 * @param list A pointer to this list.
 * @param value A value to be deleted
 */
void delete_singly(Linked_list *list, int value);

/**
 * @brief Checks whether the list is empty
 * 
 * @param list A pointer to this list. 
 * @return int 1 for empty list, 0 otherwise.
 */
int is_empty(Linked_list *list);

/**
 * @brief Dr. Gębala's findMTF function implementation for singly Linked_list
 * 
 * @param list A pointer to this list.  
 * @param value A value to found.
 * @return int 1 for succes, 0 otherwise.
 */
int findMTF_singly(Linked_list *list, int value);

/**
 * @brief Dr. Gębala's findTRANS function implementation for singly Linked_list
 * 
 * @param list A pointer to this list.  
 * @param value A value to found.
 * @return int 1 for succes, 0 otherwise.
 */
int findTRANS_singly(Linked_list *list, int value);

/**
 * @brief Prints the list.
 * 
 * @param list A Linked_list ot be printed.
 */
void print_list(Linked_list list);

#endif
