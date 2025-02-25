/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinth;

/**
 *
 * @author user
 */
public class LeaderBoardEnt {
    private final String name;
    private final int solvedLabyrinths;

    public LeaderBoardEnt(String name, int solvedLabyrinths) {
        this.name = name;
        this.solvedLabyrinths = solvedLabyrinths;
    }

    public String getName() {
        return name;
    }

    public int getSolvedLabyrinths() {
        return solvedLabyrinths;
    }

    @Override
    public String toString() {
        return name + " - Solved: " + solvedLabyrinths;
    }
}
