import { createRoute, redirect } from "@tanstack/react-router";
import { rootRoute } from "../root.routes";
import { MentorsOverview } from "../../pages/MentorsOverview/MentorsOverview.pages";

export const mentorsRoute = createRoute({
  getParentRoute: () => rootRoute,
  beforeLoad: async () => {
    const canView =
      localStorage.getItem("userRole") == "ADMIN" ||
      localStorage.getItem("userRole") == "TEAM LEAD";

    const token = localStorage.getItem("token");
    if (!canView || token == null) return redirect({ to: "/themes" });
  },
  path: "mentors-overview",
  component: MentorsOverview,
});
