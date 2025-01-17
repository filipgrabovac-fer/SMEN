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
    <div className="w-4/5 m-auto flex flex-col gap-y-6 mt-6">
      <OglasiHeader />
      <OglasiTable data={data} />
    </div>
  );
};

export default OglasOverview;
