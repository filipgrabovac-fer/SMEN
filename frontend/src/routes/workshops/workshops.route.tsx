import { createRoute, redirect } from "@tanstack/react-router";
import { rootRoute } from "../root.routes";
import { WorkshopSuggestions } from "../../pages/WorkshopSuggestions/WorkshopSuggestions.pages";
import { loginRoute } from "../auth/login.routes";

export const workshopsRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: "/workshop-suggestions",
  beforeLoad: async () => {
    const token = localStorage.getItem("token");

    if (token == null) {
      return redirect({ to: loginRoute.fullPath });
    }
  },
  component: WorkshopSuggestions,
});
