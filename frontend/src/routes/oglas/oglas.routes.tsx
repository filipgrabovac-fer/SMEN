import { createRoute, redirect } from "@tanstack/react-router";
import { rootRoute } from "../root.routes";
import OglasOverview from "../../pages/OglasOverview/OglasOverview.pages";
import { loginRoute } from "../auth/login.routes";

export const oglasRoute = createRoute({
  getParentRoute: () => rootRoute,
  beforeLoad: async () => {
    const token = localStorage.getItem("token");

    if (token == null) {
      return redirect({ to: loginRoute.fullPath });
    }
  },
  path: "/posts",
  component: OglasOverview,
});
