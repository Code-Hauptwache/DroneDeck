package main.java.controllers;

import main.java.ui.dtos.DroneDashboardDto;

import java.util.List;

/**
 * A controller that provides List of DroneDashboardDto
 */
public interface IDroneDashboardController {

    /**
     * Retrieves a paginated list of drones and generates a corresponding list of DroneDashboardDto objects.
     * This method processes the drone data asynchronously using a thread pool to enhance performance
     * when generating the dashboard card DTOs.
     *
     * @param limit the maximum number of drones to process and include in the result.
     * @param offset the starting index within the dataset from which to retrieve the drones.
     * @return a list of DroneDashboardDto objects corresponding to the drones retrieved,
     *         or an empty list if no drones are found within the specified range.
     */
    List<DroneDashboardDto> getDroneDashboardCardsThreads(int limit, int offset);

}
