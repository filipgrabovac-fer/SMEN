import { createRoute } from "@tanstack/react-router";
import { rootRoute } from "../root.routes";
import OglasOverview from "../../pages/OglasOverview/OglasOverview.pages";

export const oglasRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: "/posts",
  component: OglasOverview,
});
