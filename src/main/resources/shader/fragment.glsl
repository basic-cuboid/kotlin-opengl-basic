#version 410

in vec2 outTextureCoordinate;

uniform sampler2D textureSampler;

out vec4 color;

void main()
{
    color = texture(textureSampler, outTextureCoordinate);
}