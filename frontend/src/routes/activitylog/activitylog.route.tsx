import { createRoute } from "@tanstack/react-router";
import { rootRoute } from "../root.routes";
import { ActivityLogOverview } from "../../pages/ActivityLog/ActivityLog.pages";

export const activityLogRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: "/activity",
  component: ActivityLogOverview,
});
