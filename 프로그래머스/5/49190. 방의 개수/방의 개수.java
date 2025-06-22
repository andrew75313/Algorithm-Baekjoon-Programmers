import java.util.*;

class Solution {
    
    public int solution(int[] arrows) {
        
        // 방향 하드코딩
        int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] dy = {1, 1, 0, -1, -1, -1, 0, 1};
        
        // 방수
        int rooms = 0;
        
        // 저장 자료구조
        Set<Node> visitedNodes = new HashSet<>();
        Set<Edge> visitedEdges =  new HashSet<>();
        
        // 시작
        int currX = 0;
        int currY = 0;
        
        // 초기 세팅
        visitedNodes.add(new Node(currX, currY));
        
        for(int arrow : arrows) {
            
            // 스케일업 해서 두번씩 지날 수 있도록 함
                for(int i = 0; i < 2; i++) {

                int nextX = currX + dx[arrow];
                int nextY = currY + dy[arrow];

                Node currNode = new Node(currX, currY);
                Node nextNode = new Node(nextX, nextY);

                Edge edge =  new Edge(currNode, nextNode);

                // [방 생성 여부 검증]
                // 1. nextNode 이미 방문한 노드 = visited 일경우 &&
                // 2. edge가 새로 생길경우 = visited 아닐 경우 -> 방생성
                if(visitedNodes.contains(nextNode)
                  && !visitedEdges.contains(edge)) {
                    rooms++;
                }

                // 기록하기
                visitedNodes.add(nextNode);
                visitedEdges.add(edge);

                // 현재 좌표 갱신
                currX = nextX;
                currY = nextY;  
            }
        }
        
        return rooms;
    }
}

class Node {
    int x;
    int y;
    
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null
            || getClass() != o.getClass()) return false;
        
        Node node = (Node) o;
        
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

class Edge {
    Node node1;
    Node node2;
    
    public Edge(Node node1, Node node2) {
        // 무조건 X 오름차순, Y 오름차순으로 등록
        if(node1.x > node2.x
           || (node1.x == node2.x && node1.y > node2.y)) {
            this.node1 = node2;
            this.node2 = node1;
        } else {
            this.node1 = node1;
            this.node2 = node2;
        }
    }
    
     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null 
            || getClass() != o.getClass()) return false;
        
        Edge edge = (Edge) o;

        return node1.equals(edge.node1) && node2.equals(edge.node2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node1, node2); 
    }
}