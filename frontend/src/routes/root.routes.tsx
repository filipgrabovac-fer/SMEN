import { createRootRoute, Outlet, redirect } from "@tanstack/react-router";
import GlobalHeader from "../components/GlobalHeader";
import { Layout } from "antd";
import { loginRoute } from "./auth/login.routes";

export const rootRoute = createRootRoute({
  beforeLoad: () => {
    const token = localStorage.getItem("token");
    if (token == null) {
      redirect({ to: loginRoute.fullPath });
    }
  },
  component: () => (
    <Layout className="layout" style={{ backgroundColor: "#ffffffff" }}>
      <GlobalHeader />
      <Outlet />
    </Layout>
  ),
});
