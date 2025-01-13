import { createRoute } from "@tanstack/react-router";
import { rootRoute } from "../root.routes";
import { MentorsOverview } from "../../pages/MentorsOverview/MentorsOverview.pages";

export const mentorsRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: "mentors-overview",
  component: MentorsOverview,
});
