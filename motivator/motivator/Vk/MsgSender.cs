using System;
using System.Threading.Tasks;
using VkNet;
using VkNet.Model;
using VkNet.Model.RequestParams;

namespace motivator.Vk
{
    public class MsgSender
    {
        private const string AccessToken = "e02c29b932529372ca901a432c1858a4f1936171e2fd90c1ec4e705324d6365a763c550133aae30a900ec";
        private const long GroupId = 201671833;

        private readonly VkApi _api = new VkApi();

        public MsgSender()
        {
            _api.Authorize(new ApiAuthParams()
            { 
                AccessToken = AccessToken
            });
        }

        public async Task Send(string msg, long userId) //TODO async?
        {
            _api.Messages.Send(new MessagesSendParams()
            {
                PeerId = userId,
                Message = msg,
                RandomId = new Random().Next(),
                GroupId = GroupId
            });
        }
    }
}