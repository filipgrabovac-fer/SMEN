import { createRoute, redirect } from "@tanstack/react-router";
import { rootRoute } from "../root.routes";
import { ThemeOverview } from "../../pages/ThemeOverview/ThemeOverview.pages";
import { loginRoute } from "../auth/login.routes";

export const themeOverviewRoute = createRoute({
  getParentRoute: () => rootRoute,
  beforeLoad: async () => {
    const token = localStorage.getItem("token");

    if (token == null) {
      return redirect({ to: loginRoute.fullPath });
    }
  },
  path: "theme/$themeId",
  component: ThemeOverview,
});
