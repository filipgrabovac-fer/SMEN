//import { Outlet } from "react-router";
import CustomHeader from "../components/CustomHeader";
import { Layout } from "antd";
import LayoutPregledTema from "../features/pregledTema/layout/LayoutPregledTema";

const { Header, Footer, Content } = Layout;

const AppLayout = () => (
  <Layout className="layout">
    <Header className="header">
      <CustomHeader />
    </Header>
    <Content className="content">
      <LayoutPregledTema />
    </Content>
    <Footer className="footer">Footer</Footer>
  </Layout>
);

export default AppLayout;
