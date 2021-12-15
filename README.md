# Weighted Directed Graphs
## Assignment 2 - Object Oriented Programming 
**Netanel Levine**   
**Yanir Cohen**    
--- 
This project is about simulating and illustrating algorithms on graphs. In this project we will load graphs from Json files and will activate specific algorithms on those graphs. In addition we will create a UI that illustrates the algorithm we activated on the graph.
In this way you will be able to load and activate algorithm on the graphs as you wish.
 
---
## Project UML

<img src="https://github.com/netanellevine/Weighted_Graph_Algorithms/blob/main/uml/Arcithecture.png">

## Results
Here are the results of the algorithm on a connected graph.  
The left column has the function we activated. The first row has the number of nodes.
|        | **1000/20000** | **10000/200000** | **100000/2000000** | **1000000/200000000** |
|-----------|--------|--------|--------|--------|
|**is Connected**|	0.01s	 | 0.2s  |	 5-17s  | NULL  | 
|**TSP 10 Nodes** |0.02s	 | 0.5s   |	1s  | NULL  | 
|**Center** |2s	 | 5.5m   |	NULL   | NULL  | 
|**Shortest Path** |0.002s | 0.01s   |	5s   | NULL  | 
  
We are able to generate a graph with 1mk nodes and 20mk edges but are unable to run any algorithm on it, due to heap space.

The results vary depends on how th graph is generated.

---
## Gui 
Here we attached a picture of the gui.
Each button will activate a different algorithm.
<div align="center">
    <img src="https://github.com/netanellevine/Weighted_Graph_Algorithms/blob/main/gui/graph.PNG">
 </div>
---
## How to use
will be done soon
---
## Sources:

  - <a href="https://www.youtube.com/watch?v=CerlT7tTZfY&t">Djikstra's algorithm using a priority queue - Youtube</a>
  - <a href="https://www.geeksforgeeks.org/connectivity-in-a-directed-graph/">Check if a graph is strongly connected</a>
