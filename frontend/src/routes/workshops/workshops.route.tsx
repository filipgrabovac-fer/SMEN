import { createRoute } from "@tanstack/react-router";
import { rootRoute } from "../root.routes";
import { WorkshopSuggestions } from "../../pages/WorkshopSuggestions/WorkshopSuggestions.pages";

export const workshopsRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: "/workshop-suggestions",
  component: WorkshopSuggestions,
});
