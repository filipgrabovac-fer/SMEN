import { createRootRoute, Outlet } from "@tanstack/react-router";
import GlobalHeader from "../components/GlobalHeader";
import { Layout } from "antd";

export const rootRoute = createRootRoute({
  component: () => (
    <Layout className="layout" style={{ backgroundColor: "#ffffffff" }}>
      <GlobalHeader />
      <Outlet />
    </Layout>
  ),
});
