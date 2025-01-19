import { createRoute, redirect } from "@tanstack/react-router";
import { Themes } from "../../pages/Themes/Themes.page";
import { rootRoute } from "../root.routes";

export const themesRoute = createRoute({
  getParentRoute: () => rootRoute,
  beforeLoad: async () => {
    const token = localStorage.getItem("token");
    if (token == null) return redirect({ to: "/login" });
  },
  path: "/themes",
  component: Themes,
});
