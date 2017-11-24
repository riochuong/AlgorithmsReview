class SCC (object):
    def __init__(self, input_file):
        self.graphs = self.__constructGraphFromInputFile(input_file)
    
    def __constructGraphFromInputFile(self, input_file):
        """
         return ajacent array format 
        """
        file = open(input_file)
        graphs = {}
        for line in file:
            #print(line)
            edge = line.split(" ")
            source = int(edge[0])
            dest = int(edge[1])
            # add vertex adjacency list  
            if not (source in graphs):
                graphs[source] = [dest]
            elif not (dest in graphs[source]):
                graphs[source].append(dest)
            # need an empty list of adjacent neigbhors from each vertex
            if not (dest in graphs):
                graphs[dest] = []
        # return graphs
        #print(graphs)
        return graphs
    
    def __reversedGraph(self, graphs):
        """
         reversed graph that constructed by __constructGraphFromInputFile
        """
        revGraphs = {}
        # get all destinations v
        for source in graphs.keys():
            dests = graphs[source]
            if not (source in revGraphs):
                revGraphs [source] = []
            for dest in dests:
                if not dest in revGraphs:
                    revGraphs[dest] = [source]
                elif not source in revGraphs[dest]:
                    revGraphs[dest].append(source)
                
        
        # we have the reversed graph here 
        #print(revGraphs)
        return revGraphs
    
    def __DFS_loop (self, graphs, vertices, passOrder=1):
        """
            DFS loop on graphs
            
            Parameters:
                graphs: a DAC graphs constructed by __constructGraphFromInputFile
                passOrder: 1 for the first pass and 2 for second pass to find all SCC
        """
        explored = {}
        orderMap = {}
        leaderMap = {}
        order = [0] 
        count = 0
        for i in vertices:
            if not (i in explored):
                explored[i] = i
                self.__DFS(graphs, i, orderMap, order, explored, leaderMap, i, passOrder)
        # return information depends on pass order         
        if passOrder == 1:
            return orderMap
        else:
            return leaderMap
    
    def __DFS (self, graphs, vertex, orderMap, order, explored, leaderMap, leader, passOrder):
        """
         Do DFS on graphs 
        """
        edges = graphs[vertex]
        #print("DFS: ",vertex)
        if passOrder == 2:
            #print("add leader for ",vertex,":",leader)
            leaderMap[vertex] = leader
        for dest in edges:
            if not dest in explored:
                explored[dest] = dest
                self.__DFS(graphs, dest, orderMap, order, explored, leaderMap, leader, passOrder)
        # increment order
        if passOrder == 1:
            order[0] += 1
            # save it to map 
            orderMap[vertex] = order[0]

            
    def getOrderVertices(self):
        """
         Find all SCCS in the graph using 
         @return : array of size of each SCC. Size of the array is the number of SCCS
        """
        revGraphs = self.__reversedGraph(self.graphs)
        # do DFS_loop on revGraphs to find magic order
        vertices = reversed(list(range(1, max(revGraphs.keys()) + 1)))
        orderMap = self.__DFS_loop(revGraphs,vertices, passOrder=1)
        #print (orderMap)
        # get the list order to traverse
        verticeTuples = sorted(orderMap.items(), key=lambda x:x[1]) 
        vertices = []
        #print(verticeTuples)
        # get the list order 
        for verticeTuple in reversed(verticeTuples):
            #print(verticeTuple)
            vertices.append(verticeTuple[0])
        
        self.orderedVertices = vertices
        
      
        #print("leader ", leader)
        
        # do DFS_loop on Graphs to determine SCC and size 
    def getSCCs(self, orderedVertices):
          #print (vertices)
        leader = self.__DFS_loop(self.graphs, orderedVertices, passOrder=2)
        return leader

if __name__ == '__main__':
    import sys
    import faulthandler
    faulthandler.enable()
    sys.setrecursionlimit(200000)
    scc = SCC("SCC.txt")
    scc.getOrderVertices()
    results = scc.getSCCs(scc.orderedVertices)
    sccMap = {}
    # map on SCC to list 
    for key in results:
        parent = results[key]
        if not (parent in sccMap):
            sccMap[parent] = [key]
        else:
            sccMap[parent].append(key)
    sccMapTuples = sccMap.items()
    sccMapTuples = list(reversed(sorted(sccMapTuples, key=lambda x:len(x[1]))))
    # print top 5
    for i in range(5):
        print(len(sccMapTuples[i][1]))

