import styled from 'styled-components';

export const DashboardContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background-color: #f4f7fa;
  margin-top: 70px;
  width: 70%;
`;

export const DashboardHeader = styled.header`
  width: 100%;
  background-color: #007bff;
  color: white;
  padding: 10px 0;
  text-align: center;
  border-radius: 8px 8px 0 0;
`;

export const DashboardMain = styled.div`
  display: flex;
  width: 100%;
  justify-content: space-between;
  gap: 20px;
  padding: 20px 0;
`;

export const DashboardContent = styled.main`
  display: flex;
  flex-direction: column;
  width: 70%;
`;

export const DashboardSidebar = styled.aside`
  width: 30%;
`;

export const Card = styled.div`
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin: 0.5rem 0;
  width: 100%;
  max-width: 800px;

  h2 {
    margin-top: 0;
    padding-bottom: 10px;
  }

  div {
    margin: 10px 0;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;

    p {
      display: flex;
      align-items: center;
      gap: 5px;
      width: 100%;
    }
  }
  
  p {
    margin: 5px 0;
  }

  ul {
    list-style: none;
    padding: 0;

    li {
      background: #f4f7fa;
      margin: 5px 0;
      padding: 10px;
      border-radius: 4px;
    }
  }

  img {
    max-width: 100%;
    border-radius: 8px;
    margin-top: 10px;
  }

  button {
    background: #007bff;
    color: white;
    border: none;
    padding: 10px;
    border-radius: 4px;
    width: 100%;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 5px;
  }
`;

export const ConfirmationContainer = styled(DashboardContainer)``;

export const ConfirmationHeader = styled(DashboardHeader)``;

export const ConfirmationContent = styled(DashboardContent)``;

export const Message = styled.p`
  font-size: 18px;
  color: #4caf50;
  margin: 20px 0;
`;
