package za.scratch.game.matrix.generator.utils;

import za.scratch.game.common.models.config.ProbabilitySymbols;
import za.scratch.game.common.models.config.StandardSymbols;
import za.scratch.game.common.models.matrix.MatrixRequest;

import java.util.*;

public class MatrixUtils {

    private static Double sumOfValues(Map<String, Integer> symbols) {
        Double sum = (double) 0;
        for (Integer count : symbols.values()) {
            sum += count;
        }
        return sum;
    }

    private static Map<String, Double> calculateProbabilities(Map<String, Integer> symbols) {
        Double sum = sumOfValues(symbols);
        Map<String, Double> valueProbabilities = new HashMap<>();
        for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
            double count = entry.getValue();
            double probability = count / sum;
            valueProbabilities.put(entry.getKey(), probability);
        }

        return valueProbabilities;
    }

    private static Map<String, Double> getStringDoubleMap(ProbabilitySymbols probabilitySymbols, String position) {
        var standardSymbols = probabilitySymbols.getStandard_symbols().stream().parallel()
                .filter(p -> (p.getRow() + "," + p.getColumn()).equals(position))
                .findFirst();

        return standardSymbols.stream().parallel()
                .map(StandardSymbols::getSymbols)
                .map(MatrixUtils::calculateProbabilities)
                .findAny()
                .orElseThrow();
    }

    private static String selectRandomSymbol(Map<String, Double> symbolProbabilities) {
        double rand = Math.random();
        double cumulativeProbability = 0.0;

        for (Map.Entry<String, Double> entry : symbolProbabilities.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (rand <= cumulativeProbability) {
                return entry.getKey();
            }
        }

        return (String) symbolProbabilities.keySet().toArray()[symbolProbabilities.size() - 1];
    }


    public static List<List<String>> createMatrix(MatrixRequest request) {
        var columns = request.columns();
        var rows = request.rows();
        var probabilitySymbols = request.probabilitySymbols();
        var bonusSet = false;
        var random = new Random();

        List<List<String>> matrix = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                String position = i + "," + j;
                var symbols = getStringDoubleMap(probabilitySymbols, position);

                var bonus = calculateProbabilities(probabilitySymbols.getBonus_symbols().getSymbols());

                var symbol = selectRandomSymbol(symbols);
                if((random.nextInt()/random.nextDouble()*i) < (random.nextInt()*j/random.nextDouble())){
                  if(!bonusSet)
                      symbol = selectRandomSymbol(bonus);
                    bonusSet = true;
                }

                row.add(symbol);
            }
            matrix.add(row);
        }

        return matrix;
    }



}
