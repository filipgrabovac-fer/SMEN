import PrijavaModal from "./components/PrijavaModal";

const PrijavaZaMentorstvoLayout = ({
  open,
  onClose,
}: {
  open: boolean;
  onClose: () => void;
}) => {
  // // @ts-expect-error: userId is always defined at this point
  // const { userId } = jwtDecode(localStorage.getItem("token") ?? "");
  const userId = 1;
  return <PrijavaModal requesterId={userId} open={open} onClose={onClose} />;
};

export default PrijavaZaMentorstvoLayout;
