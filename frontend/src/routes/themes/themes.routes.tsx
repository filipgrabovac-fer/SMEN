import { createRoute } from "@tanstack/react-router";
import { Themes } from "../../pages/Themes/Themes.page";
import { rootRoute } from "../root.routes";

export const themesRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: "/themes",
  component: Themes,
});
