import { createRoute } from "@tanstack/react-router";
import { rootRoute } from "../root.routes";
import { WorkshopUserOverview } from "../../pages/WorkshopUserOverview/WorkshopUserOverview.pages";

export const workshopsUserRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: "/workshop-user",
  component: WorkshopUserOverview,
});
