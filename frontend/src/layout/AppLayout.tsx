import CustomHeader from "../components/GlobalHeader";
import { Layout } from "antd";
import { Outlet } from "react-router";

const { Header, Content } = Layout;

const AppLayout = () => (
  <Layout className="layout">
    <Header className="header">
      <CustomHeader />
    </Header>
    <Content className="content">
      <Outlet />
    </Content>
  </Layout>
);

export default AppLayout;
