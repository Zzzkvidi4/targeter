FROM mcr.microsoft.com/dotnet/sdk:5.0 AS build-env

# Copy csproj and restore as distinct layers
COPY /motivator/*.csproj /app/

# Copy everything else and build
COPY /motivator /app/

WORKDIR /app
RUN dotnet restore
RUN dotnet publish -c Release -o out

# Build runtime image
FROM mcr.microsoft.com/dotnet/aspnet:5.0
WORKDIR /app
COPY --from=build-env /app/out .
ENTRYPOINT ["dotnet", "motivator.dll"]