package util;

import model.Car;
import java.util.*;
import java.util.stream.Collectors;

public class SmartSuggestion {

    // Gợi ý từ danh sách yêu thích - home.jsp
    public static List<Car> suggestFromFavorite(List<Car> favorites, List<Car> allCars) {
        Set<Integer> favoriteIds = favorites.stream()
                .map(Car::getCarId)
                .collect(Collectors.toSet());

        // Map để lưu: candidateId -> số điểm trùng khớp
        Map<Car, Integer> matchMap = new HashMap<>();

        for (Car candidate : allCars) {
            if (favoriteIds.contains(candidate.getCarId())) {
                continue;
            }

            if (candidate.getStockQuantity() <= 0) {
                continue;
            }

            int matchScore = 0;
            for (Car fav : favorites) {
                if (Objects.equals(candidate.getCarBrandName(), fav.getCarBrandName())) {
                    matchScore++;
                }
                if (Objects.equals(candidate.getCarModelName(), fav.getCarModelName())) {
                    matchScore++;
                }
                if (Objects.equals(candidate.getFuelType(), fav.getFuelType())) {
                    matchScore++;
                }
                if (Objects.equals(candidate.getTransmission(), fav.getTransmission())) {
                    matchScore++;
                }
            }

            if (matchScore >= 1) { // Ít nhất có điểm tương đồng
                matchMap.put(candidate, matchMap.getOrDefault(candidate, 0) + matchScore);
            }
        }

        return matchMap.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // Giảm dần theo điểm
                .limit(6)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // Gợi ý từ 1 xe cụ thể - carDetail.jsp
    public static List<Car> suggestFromCar(Car target, List<Car> allCars) {
        Map<Car, Integer> matchMap = new HashMap<>();

        for (Car candidate : allCars) {
            if (candidate.getCarId().equals(target.getCarId())) {
                continue;
            }

            if (candidate.getStockQuantity() <= 0) {
                continue;
            }

            int matchScore = 0;
            if (Objects.equals(candidate.getCarBrandName(), target.getCarBrandName())) {
                matchScore++;
            }
            if (Objects.equals(candidate.getCarModelName(), target.getCarModelName())) {
                matchScore++;
            }
            if (Objects.equals(candidate.getFuelType(), target.getFuelType())) {
                matchScore++;
            }
            if (Objects.equals(candidate.getTransmission(), target.getTransmission())) {
                matchScore++;
            }

            if (matchScore >= 1) {
                matchMap.put(candidate, matchScore);
            }
        }

        return matchMap.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(4)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static List<Car> suggestFromViewed(List<Car> viewed, List<Car> allCars, int max) {
        Map<Car, Integer> carMatchScores = new HashMap<>();

        for (Car candidate : allCars) {
            if (viewed.stream().anyMatch(v -> v.getCarId().equals(candidate.getCarId()))) {
                continue;
            }

            if (candidate.getStockQuantity() <= 0) {
                continue;
            }

            int score = 0;
            for (Car v : viewed) {
                if (Objects.equals(candidate.getCarBrandName(), v.getCarBrandName())) {
                    score++;
                }
                if (Objects.equals(candidate.getCarModelName(), v.getCarModelName())) {
                    score++;
                }
                if (Objects.equals(candidate.getFuelType(), v.getFuelType())) {
                    score++;
                }
                if (Objects.equals(candidate.getTransmission(), v.getTransmission())) {
                    score++;
                }
            }

            if (score >= 2) {
                carMatchScores.put(candidate, score);
            }
        }

        return carMatchScores.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue()) // Sắp xếp giảm dần theo độ tương đồng
                .map(Map.Entry::getKey)
                .limit(max)
                .toList();
    }
}
