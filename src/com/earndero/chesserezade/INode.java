package com.earndero.chesserezade;

import java.io.IOException;

public interface INode {
    boolean is_a_terminal_node();

    int heuristic_value_of();

    INodeList genChilds() throws IOException;

    void takeBackMove();

    void makeMove() throws Exception;
}
