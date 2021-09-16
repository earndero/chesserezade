package com.earndero.chesserezade;

public class Searcher {
    public static int minimax(INode node, int depth, boolean maximizingPlayer) throws Exception {
        if (depth == 0 || node.is_a_terminal_node())
            return node.heuristic_value_of();
        INodeList childs = node.genChilds();
        if (maximizingPlayer) {
            int value = Integer.MIN_VALUE;
            INode child;
            while ((child=childs.getNext())!=null) {
                child.makeMove();
                value = Math.max(value, minimax(child, depth - 1, false));
                child.takeBackMove();
            }
            return value;
        } else {
            int value = Integer.MAX_VALUE;
            INode child;
            while ((child=childs.getNext())!=null) {
                child.makeMove();
                value = Math.min(value, minimax(child, depth - 1, true));
                child.takeBackMove();
            }
            return value;
        }
    }
}