package graphs;

import java.util.*;

/**
     * The class TaskScheduler allows
     * to declare a set of tasks with their dependencies.
     * You have to implement the method:
     *      boolean isValid(List<String> schedule)
     * allowing to verify if the given schedule does
     * not violate any dependency constraint.
     *
     * Example:
     *
     *         TaskScheduler scheduler = new TaskScheduler();
     *         scheduler.addTask("A", Arrays.asList());
     *         scheduler.addTask("B", Arrays.asList("A"));
     *         scheduler.addTask("C", Arrays.asList("A"));
     *         scheduler.addTask("D", Arrays.asList("B", "C"));
     *
     *    The dependency graph is represented as follows:
     *
     *          │─────► B │
     *        A─│         ├─────►D
     *          │─────► C │
     *
     *
     *         assertTrue(scheduler.isValid(Arrays.asList("A", "B", "C", "D")));
     *         assertFalse(scheduler.isValid(Arrays.asList("A", "D", "C", "B"))); // D cannot be scheduled before B
     *
     *  Feel free to use existing java classes.
     */
    public class TaskScheduler {
        private Map<String, List<String>> graph;


        public TaskScheduler() {
            this.graph = new HashMap<>();
        }

        /**
         * Adds a task with the given dependencies to the scheduler.
         * The task cannot be scheduled until all of its dependencies have been completed.
         */
        public void addTask(String task, List<String> dependencies) {
            this.graph.put(task, dependencies);
        }

        /**
         * Verify if the given schedule is valid, that is it does not violate the dependencies
         * and every task in the graph occurs exactly once in it.
         * The time complexity of the method should be in O(V+E) where
         * V = number of tasks, and E = number of requirements.
         * @param schedule a list of tasks to be scheduled in the order they will be executed.
         */
        public boolean isValid(List<String> schedule) {
            // I'd go with a topological sort (?)
            if (schedule.size() != this.graph.size()) return false;
            Map<String, List<String >> remainingTasks = new HashMap<>(this.graph);

            for (String task : schedule) {
                if (!remainingTasks.containsKey(task)) return false;
                for (String dependency : remainingTasks.get(task)) {
                    if (remainingTasks.containsKey(dependency)) return false;
                }
                remainingTasks.remove(task);
            }
            return true;
        }


    }