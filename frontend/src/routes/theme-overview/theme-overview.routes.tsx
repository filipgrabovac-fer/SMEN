import { createRoute } from "@tanstack/react-router";
import { rootRoute } from "../root.routes";
import { ThemeOverview } from "../../pages/ThemeOverview/ThemeOverview.pages";

export const themeOverviewRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: "theme/$themeId",
  component: ThemeOverview,
});
