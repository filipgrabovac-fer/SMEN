import { Flex } from "antd";
import OglasiHeader from "./components/OglasiHeader";
import OglasiTable from "./components/OglasiTable";
import { useGetPosts } from "./hooks/useGetPosts.hook";

const OglasOverview = () => {
  const { data: postsData } = useGetPosts();

  const data =
    postsData?.map((post, i) => ({
      key: i.toString(),
      autor: "Mock Post Author",
      opis: post.description,
      datum: new Date(),
      naslovOglasa: post.title,
      details: `https://example.com/${post.id}`,
    })) ?? [];

  return (
    <Flex
      className="w-full"
      justify="center"
      style={{
        width: "80%",
        height: "80%",
        margin: "0 auto",
      }}
      vertical
    >
      <OglasiHeader />
      <OglasiTable data={data} />
    </Flex>
  );
};

export default OglasOverview;
