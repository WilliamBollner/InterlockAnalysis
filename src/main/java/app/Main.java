package app;

import data.ExcelReader;
import graph.GraphBuilder;
import org.graphstream.graph.Graph;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Por favor, insira o caminho do arquivo Excel:");
        String excelFilePath = scanner.nextLine();

        try {
            // Read data from Excel
            ExcelReader reader = new ExcelReader();
            List<Object> rawData = reader.readExcel(excelFilePath);

            // Build the graph
            GraphBuilder builder = new GraphBuilder();
            Graph graph = builder.buildGraph(rawData);

            // Configuration to visualize the graph
            System.setProperty("org.graphstream.ui", "swing");
            graph.display();

        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}