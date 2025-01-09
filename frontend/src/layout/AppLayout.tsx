//import { Outlet } from "react-router";
import CustomHeader from "../components/CustomHeader";
import { Layout } from "antd";
import LayoutPregledOglasa from "../features/pregledOglasa/layout/LayoutPregledOglasa";

const { Header, Footer, Content } = Layout;

const AppLayout = () => (
  <Layout className="layout">
    <Header className="header">
      <CustomHeader />
    </Header>
    <Content className="content">
      <LayoutPregledOglasa />
    </Content>
    <Footer className="footer">Footer</Footer>
  </Layout>
);

export default AppLayout;
